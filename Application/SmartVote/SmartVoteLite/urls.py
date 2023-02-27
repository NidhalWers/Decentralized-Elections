from django.urls import path

from . import views

urlpatterns = [
    path('parametrelite', views.parametre, name='parametrelite'),
    path('success/<int:code>', views.success, name='success'),
    path('successVote', views.successVote, name='successVote'),
    path('entercode',views.enterCode, name='entercode'),
    path('verification/<int:code>',views.verification, name='verification'),
    path('viewElectionResults/<str:name>', views.resultElection, name='resultElection'),
    path('', views.home, name='homelite'),
    path('viewElection/<int:code>', views.viewElection, name='viewElection'),
    path('viewElectionResults/<str:code>', views.resultElection, name='viewElectionResults'),
    path('api/getUserIdCrypted', views.getIdEncrypted, name='getIdEncrypted'),
    path('api/getAPIKEYDecrypted', views.getAPIKeyDecrypted, name='getAPIKeyDecrypted'),
    path('api/getCandidatesInElection/<str:election>', views.getCandidatesInElection, name='getCandidatesInElection'),
    path('api/getCandidate/<str:pk>', views.getCandidate, name='getCandidate'),
    path('api/addCandidate', views.addCandidate, name='addCandidate'),
    path('api/delCandidate/<str:pk>/<str:election>', views.delCandidate, name='delCandidate'),
    path('api/updateCandidate/<str:pk>/<str:election>', views.updateCandidate, name='updateCandidate'),
    path('api/addElection', views.addElection, name='addElection'),
    path('api/getElections', views.getElections, name='getElections'),
    path('api/getElection/<str:code>', views.getElection, name='getElection'),
    path('api/updateElection/<str:pk>', views.updateElection, name='updateElection'),
    path('api/isElectionExist/<str:name>', views.isElectionExistAPI, name='isElectionExistAPI'),
]