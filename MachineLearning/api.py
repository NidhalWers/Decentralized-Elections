from flask import Flask, request, jsonify, send_file
import re
import matplotlib.pyplot as plt
import matplotlib
import numpy as np
import pandas as pd
import io

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


# Les termes prépondérants dans ces programmes
def plot_top_words(model, feature_names, n_top_words=20):
    fig, axes = plt.subplots(2, 3, figsize=(20, 10))
    for i, (topic, ax) in enumerate(zip(model.components_, axes.flatten())):
        top_features_ind = topic.argsort()[:-n_top_words - 1:-1]
        top_features = [feature_names[i] for i in top_features_ind]
        top_features_val = topic[top_features_ind]
        ax.barh(top_features, top_features_val)
        ax.set_title(f"Topic {i}")
        ax.invert_yaxis()
    plt.tight_layout()
    plt.show()

app = Flask(__name__)

# fonction qui retourne le candidat le plus proche du theme choisi
@app.route('/api/nearest_theme', methods=['GET'])
def get_nearest_theme():
    # Récupération du thème depuis la requête
    theme = request.args.get('theme')
    vec = TfidfVectorizer(strip_accents="unicode", lowercase=True, stop_words=stop_words)
    vec.fit(data["program_text"])
    vec_theme = vec.transform([theme])
    nbrs = NearestNeighbors(n_neighbors=1, algorithm="ball_tree").fit(vec_program.toarray())
    distances, indices = nbrs.kneighbors(vec_theme.toarray())
    nearest_program = data.iloc[indices[0][0]]
# Renvoi du nom du programme le plus proche
    return jsonify(nearest_program["name"])

# fonction qui rassemble les candidats les plus proches du theme choisi
@app.route('/api/nearest_candidates', methods=['GET'])
def get_nearest_candidates(n_candidates=5):
    theme = request.args.get('theme')
    vec = TfidfVectorizer(strip_accents="unicode", lowercase=True, stop_words=stop_words)
    vec.fit(data["program_text"])
    vec_theme = vec.transform([theme])
    nbrs = NearestNeighbors(n_neighbors=n_candidates, algorithm="ball_tree").fit(vec_program.toarray())
    distances, indices = nbrs.kneighbors(vec_theme.toarray())
    nearest_candidates = data.iloc[indices[0]]["name"].tolist()
    return jsonify(nearest_candidates)



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


# Créer une route pour votre API
@app.route('/api/results2', methods=['GET'])
def get_results2():
    # code pour exécuter votre modèle et récupérer les résultats
    # par exemple, pour les graphes :
    lda = LatentDirichletAllocation(n_components=6, random_state=0)
    lda.fit(vec_program)
    plot_top_words(lda, vec_feature_names)
    buffer = io.BytesIO()
    plt.savefig(buffer, format='png')
    buffer.seek(0)
    return send_file(buffer, mimetype='image/png')

# Lancer l'application Flask
if __name__ == '__main__':
    app.run(debug=True, port=8000)
