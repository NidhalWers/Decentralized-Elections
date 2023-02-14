from django.urls import path

from Vote import views

urlpatterns = [
    path('', views.index, name='home'),
    path('viewElectionStatus/<str:name>/<str:status>', views.viewElectionStatus, name='viewElectionStatus'),
    path('viewElection/<str:name>', views.viewElection, name='viewElection'),

    path('viewElectionResults/<str:name>', views.resultElection, name='viewElectionResults'),
    path('viewElectionResultsStatus/<str:name>/<str:status>', views.resultElectionStatus, name='viewElectionResultsStatus'),
    path('api/getCandidates', views.getCandidates, name='getCandidates'),
    path('api/getCandidate/<str:pk>', views.getCandidate, name='getCandidate'),

    path('api/getElections', views.getElections, name='getElections'),
    path('api/getElectionStatus/<str:name>/<str:status>', views.getElectionStatus, name='getElectionStatus'),
    path('api/getElection/<str:name>', views.getElection, name='getElection'),

    path('api/isElectionExistStatus/<str:name>/<str:status>', views.isElectionExistStatusAPI, name='isElectionExistStatusAPI'),
    path('api/isElectionExist/<str:name>', views.isElectionExistAPI, name='isElectionExistAPI'),

    path('api/getAPIKEYDecrypted/<str:apikey>', views.getAPIKeyDecrypted, name='getAPIKeyDecrypted'),
]