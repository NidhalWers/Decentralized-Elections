from django.urls import path, include

from . import views

urlpatterns = [
    path('login', views.login, name='connect'),
    path('login/franceconnect', views.fc, name='fc'),
    path('login/franceconnect/impotsgouvfr', views.impots, name='impots'),
    path('login/franceconnect/fcamelifr', views.ameli, name='ameli'),
]