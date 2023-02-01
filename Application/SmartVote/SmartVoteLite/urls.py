from django.urls import path

from SmartVoteLite import views

urlpatterns = [
    path('', views.home, name='home'),
]