from flask import Flask, request, jsonify, send_file
import re
import matplotlib.pyplot as plt
import matplotlib
import numpy as np
import pandas as pd
import io
import json
from flask_cors import CORS

from matplotlib_inline.backend_inline import set_matplotlib_formats
from sklearn.decomposition import LatentDirichletAllocation, NMF, PCA
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neighbors import NearestNeighbors
from wordcloud import WordCloud

set_matplotlib_formats("retina")

data = pd.read_csv("data.csv")

matplotlib.use('Agg')

# Vectorisation 

names = data["name"]

name_tokens = list(set(re.split(" |-", " ".join(names).lower())))
stop_words = [
    "000", "001", "044", "063", "093", "00139", "00296voi",
    "la", "le", "les", "ce", "ma",
    "je", "il", "elle", "nous", "vous", "ils", "elles",
    "ai", "ont", "est", "etre", "ete", "sera", "serai", "seront", "suis", "sont",
    "nos", "notre", "votre", "sa", "son",
    "de", "du", "des", "et", "en", "un", "une", "ans", "au", "aux", "se", "me", "ne", "ni", "ou",
    "qu", "que", "qui", "rai",
    "pas", "par", "pour", "dans", "dont", "sur", "avec", "contre", "mais", "plus", "jusqu",
    "france", "francais",
    *name_tokens,
]

vectorizer = TfidfVectorizer(strip_accents="unicode", lowercase=True, stop_words=stop_words)
vec_program = vectorizer.fit_transform(data["program_text"])
vec_feature_names = vectorizer.get_feature_names_out()

pd.DataFrame(vec_feature_names)

candidates_names = ["Madame Nathalie ARTHAUD", "Monsieur François ASSELINEAU", "Madame Marine LE PEN", "Monsieur Emmanuel MACRON", "Monsieur Jean-Luc MÉLENCHON", "Monsieur Philippe POUTOU"]

def generate_wordcloud_data(data, candidate_name, n_top_words=20):
    idx = candidates_names.index(candidate_name)
    wc = WordCloud(background_color="white", max_words=n_top_words)
    wc.generate_from_frequencies(dict(zip(vec_feature_names, data[idx])))
    return wc.to_array()

# vue globale des programmes de chaque candidat
def plot_wordclouds(data, names, n_top_words=20):
    fig, axes = plt.subplots(2, 3, figsize=(20, 10))
    for i, (name, ax) in enumerate(zip(names, axes.flatten())):
        wc = WordCloud(background_color="white", max_words=n_top_words)
        wc.generate_from_frequencies(dict(zip(vec_feature_names, data[i])))
        ax.imshow(wc, interpolation="bilinear")
        ax.set_title(name)
        ax.axis("off")
    plt.ion()
    plt.show()

app = Flask(__name__)
CORS(app)

@app.route('/nearest_theme/<theme>', methods=['GET'])
def nearest_theme(theme):
    vec = TfidfVectorizer(strip_accents="unicode", lowercase=True, stop_words=stop_words)
    vec.fit(data["program_text"])
    vec_theme = vec.transform([theme])
    nbrs = NearestNeighbors(n_neighbors=1, algorithm="ball_tree").fit(vec_program.toarray())
    distances, indices = nbrs.kneighbors(vec_theme.toarray())
    candidate = data.iloc[indices[0][0]]["name"]
    return json.dumps({"candidate": candidate})

@app.route('/nearest_candidates/<theme>', methods=['GET'])
def get_nearest_candidates(theme, n_candidates=5):
    vec = TfidfVectorizer(strip_accents="unicode", lowercase=True, stop_words=stop_words)
    vec.fit(data["program_text"])
    vec_theme = vec.transform([theme])
    nbrs = NearestNeighbors(n_neighbors=n_candidates, algorithm="ball_tree").fit(vec_program.toarray())
    distances, indices = nbrs.kneighbors(vec_theme.toarray())
    closest_candidate = data.iloc[indices[0]]["name"].tolist()
    return json.dumps({"closest_candidate": closest_candidate})



@app.route('/wordcloud/<candidate_name>', methods=['GET'])
def wordcloud(candidate_name):
    data = vec_program.toarray()
    wordcloud_data = generate_wordcloud_data(data, candidate_name)
    json_data = {
        "candidate_name": candidate_name,
        "wordcloud_data": wordcloud_data.tolist()
    }
    return jsonify(json_data)


# Créer une route pour votre API
@app.route('/api/results', methods=['GET'])
def get_results():
    # code pour exécuter votre modèle et récupérer les résultats
    # par exemple, pour les graphes :
    plot_wordclouds(vec_program.toarray(), names)
    buffer = io.BytesIO()
    plt.savefig(buffer, format='png')
    buffer.seek(0)
    return send_file(buffer, mimetype='image/png')

if __name__ == '__main__':
    app.run(debug=True)