from django.urls import path, include

from Connect import views

urlpatterns = [
    path('login', views.login_html, name='connect'),
    path('login/franceconnect', views.fc_html, name='fc'),
    path('login/franceconnect/impotsgouvfr', views.login_impots, name='login_impots'),
    path('login/franceconnect/fcamelifr', views.ameli_html, name='ameli'),
]