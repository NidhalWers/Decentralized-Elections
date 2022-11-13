from django.urls import path

from . import views

urlpatterns = [
    path('administrateur', views.administrateur, name='administrateur'),
    path('parametre', views.parametre, name='parametre'),
    path('', views.index, name='index'),
    path('api/getCandidates', views.getCandidates, name='getCandidates'),
    path('api/addCandidate', views.addCandidate, name='addCandidate'),
    path('api/delCandidate/<str:pk>', views.delCandidate, name='delCandidate'),
    path('api/candidates', views.delCandidate, name='delCandidate'),
    path('api/candidateDetails/<str:pk>', views.candidate_detail, name='candidate_detail'),
    # path('api/addCandidate', views.addCandidate, name='addCandidate'),
    # path('api/getCandidate', views.getCandidate, name='getCandidate'),
]