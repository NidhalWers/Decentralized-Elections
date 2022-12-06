from django.urls import path

from . import views

urlpatterns = [
    path('parametre', views.parametre, name='parametre'),
    path('success', views.success, name='success'),
    path('', views.index, name='index'),
    path('api/getCandidates', views.getCandidates, name='getCandidates'),
    path('api/addCandidate', views.addCandidate, name='addCandidate'),
    path('api/delCandidate/<str:pk>', views.delCandidate, name='delCandidate'),
    path('api/updateCandidate/<str:pk>', views.updateCandidate, name='delCandidate'),
    path('api/addElection', views.addElection, name='addElection'),
    path('api/getElections', views.getElections, name='getElections'),
    path('api/getElection/<str:name>/<str:status>/', views.getElection, name='getElection'),
    path('api/getElection/<str:name>', views.getElection, name='getElection'),
]