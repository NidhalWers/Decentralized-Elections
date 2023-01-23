from django.urls import path

from . import views

urlpatterns = [
    path('parametre', views.parametre, name='parametre'),
    path('success', views.success, name='success'),
    path('', views.index, name='index'),
    path('connection', views.connect_admin, name='connect_admin'),
    path('viewElectionStatus/<str:name>/<str:status>', views.viewElectionStatus, name='viewElectionStatus'),
    path('viewElection/<str:name>', views.viewElection, name='viewElection'),
    path('viewCandidates', views.viewCandidates, name='viewCandidates'),
    path('api/getCandidates', views.getCandidates, name='getCandidates'),
    path('api/getCandidate/<str:pk>', views.getCandidate, name='getCandidate'),
    path('api/addCandidate', views.addCandidate, name='addCandidate'),
    path('api/delCandidate/<str:pk>', views.delCandidate, name='delCandidate'),
    path('api/updateCandidate/<str:pk>', views.updateCandidate, name='updateCandidate'),
    path('api/addElection', views.addElection, name='addElection'),
    path('api/getElections', views.getElections, name='getElections'),
    path('api/getElectionStatus/<str:name>/<str:status>', views.getElectionStatus, name='getElectionStatus'),
    path('api/getElection/<str:name>', views.getElection, name='getElection'),
    path('api/delElectionStatus/<str:pk>/<str:status>', views.delElectionStatus, name='delElection'),
    path('api/delElection/<str:pk>', views.delElection, name='delElection'),
    path('api/updateElection/<str:pk>', views.updateElection, name='updateElection'),
    path('api/updateElectionStatus/<str:pk>/<str:status>', views.updateElectionStatus, name='updateElection'),

]