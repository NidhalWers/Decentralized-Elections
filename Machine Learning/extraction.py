import io
import logging
import pandas as pd
import pdfplumber
import os
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

def get_candidate_data(pdf_folder: str) -> pd.DataFrame:
    logger.info("Getting candidate data")

    rows = []
    for pdf_file in os.listdir(pdf_folder):
        if pdf_file.endswith('.pdf'):
            data = {
                "name": pdf_file,
                "program_text": extract_pdf_text(open(os.path.join(pdf_folder, pdf_file), 'rb').read())
            }

            rows.append(data)

    return pd.DataFrame(rows)

def save_to_csv(data: pd.DataFrame) -> None:
    logger.info(f"Saving data to: {csv_path}")
    csv_path.unlink(missing_ok=True)

    data.to_csv(csv_path, index=False)

def main() -> None:
    pdf_folder = input("Enter the path to the folder containing the PDF files: ")
    data = get_candidate_data(pdf_folder)
    save_to_csv(data)

if __name__ == "__main__":
    main()
