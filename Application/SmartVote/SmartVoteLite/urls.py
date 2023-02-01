from django.urls import path

from . import views

urlpatterns = [
    path('parametrelite', views.parametre, name='parametrelite'),
    path('success', views.success, name='success'),
    path('', views.home, name='homelite'),
    path('viewElectionStatus/<str:name>/<str:status>', views.viewElectionStatus, name='viewElectionStatus'),
    path('viewElection/<str:name>', views.viewElection, name='viewElection'),
    path('viewElectionResults/<str:name>', views.resultElection, name='viewElectionResults'),
    path('viewElectionResultsStatus/<str:name>/<str:status>', views.resultElectionStatus, name='viewElectionResultsStatus'),
    path('api/getCandidatesInElection/<str:election>', views.getCandidatesInElection, name='getCandidatesInElection'),
    path('api/getCandidate/<str:pk>', views.getCandidate, name='getCandidate'),
    path('api/addCandidate', views.addCandidate, name='addCandidate'),
    path('api/delCandidate/<str:pk>/<str:election>', views.delCandidate, name='delCandidate'),
    path('api/updateCandidate/<str:pk>/<str:election>', views.updateCandidate, name='updateCandidate'),
    path('api/addElection', views.addElection, name='addElection'),
    path('api/getElections', views.getElections, name='getElections'),
    path('api/getElectionStatus/<str:name>/<str:status>', views.getElectionStatus, name='getElectionStatus'),
    path('api/getElection/<str:name>', views.getElection, name='getElection'),
    path('api/delElectionStatus/<str:pk>/<str:status>', views.delElectionStatus, name='delElection'),
    path('api/delElection/<str:pk>', views.delElection, name='delElection'),
    path('api/updateElection/<str:pk>', views.updateElection, name='updateElection'),
    path('api/updateElectionStatus/<str:pk>/<str:status>', views.updateElectionStatus, name='updateElection'),
    path('api/isElectionExistStatus/<str:name>/<str:status>', views.isElectionExistStatusAPI, name='isElectionExistStatusAPI'),
    path('api/isElectionExist/<str:name>', views.isElectionExistAPI, name='isElectionExistAPI'),
]