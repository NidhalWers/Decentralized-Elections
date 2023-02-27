import io
import logging
import pandas as pd
import pdfplumber
import requests
from pathlib import Path
from urllib.parse import urljoin
from bs4 import BeautifulSoup

logging.basicConfig(level=logging.ERROR)
logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)

csv_path = (Path.cwd() / "data.csv").resolve()

def extract_pdf_text(pdf_data: bytes) -> str:
    pdf_text = ""
    with pdfplumber.open(io.BytesIO(pdf_data)) as pdf:
        for page in pdf.pages:
            pdf_text += page.extract_text(layout=True)
    return pdf_text

def get_candidate_data() -> pd.DataFrame:
    logger.info("Getting candidate data")

    candidates_url = "https://www.cnccep.fr/candidats.html"
    rv = requests.get(candidates_url)
    rv.raise_for_status()

    soup = BeautifulSoup(rv.text, "html.parser")
    candidates = soup.select(".candidats .mediascandidats .inner-content")

    rows = []
    for candidate in candidates:
        data = {
            "name": candidate.select_one("h6").text,
            "program_url": urljoin(candidates_url, candidate.select_one(".lien:nth-child(3) > a").get("href")),
            "easy_read_url": urljoin(candidates_url, candidate.select_one(".lien:nth-child(4) > a").get("href")),
        }
        logger.info(f'Processing candidate: {data["name"]}')

        program_rv = requests.get(data["program_url"])
        data["program_text"] = extract_pdf_text(program_rv.content)

        easy_read_rv = requests.get(data["easy_read_url"])
        data["easy_read_text"] = extract_pdf_text(easy_read_rv.content)

        rows.append(data)

    return pd.DataFrame(rows)

def save_to_csv(data: pd.DataFrame) -> None:
    logger.info(f"Saving data to: {csv_path}")
    csv_path.unlink(missing_ok=True)

    data.to_csv(csv_path, index=False)

def main() -> None:
    data = get_candidate_data()
    save_to_csv(data)

if __name__ == "__main__":
    main()
