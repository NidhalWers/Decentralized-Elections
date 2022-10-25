from django.urls import path

from Vote import views

urlpatterns = [
    path('', views.home, name='home'),

]