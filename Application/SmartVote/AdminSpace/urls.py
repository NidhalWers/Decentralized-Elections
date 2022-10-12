from django.urls import path, include

from . import views

urlpatterns = [
    path('alpha', views.alpha, name='admin'),
    path('administrateur', views.administrateur, name='administrateur'),
    path('beta', views.beta, name='beta'),
    path('gamma', views.gamma, name='gamma'),
    path('lamda', views.lamnda, name='lamda'),
    path('epsilon', views.epsilon, name='epsilon'),
    path('zelda', views.zelda, name='zelda'),
    
]