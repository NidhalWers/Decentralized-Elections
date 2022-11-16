from django.urls import path

from Vote import views

urlpatterns = [
    path('', views.selectVote, name='selectVote'),
    path('vote/', views.vote, name='vote'),
]