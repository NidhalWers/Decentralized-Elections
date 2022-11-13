from django.urls import path

from . import views

urlpatterns = [
    path('administrateur', views.administrateur, name='administrateur'),
    path('parametre', views.parametre, name='parametre'),
    path('', views.index, name='index'),
    path('api/candidates', views.candidate, name='candidates'),
    path('api/addCandidates', views.addCandidate, name='addCandidate'),
    path('api/candidates/<int:pk>/', views.candidate_detail),
    # path('api/addCandidate', views.addCandidate, name='addCandidate'),
    # path('api/getCandidate', views.getCandidate, name='getCandidate'),
]