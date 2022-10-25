from django.shortcuts import render

# Create your views here.
from django.contrib.auth.models import User
from django.shortcuts import render,redirect
from django.contrib import auth
from django.shortcuts import render,redirect
from django.contrib.auth import authenticate, login

from Connect.models import Citizen

# Create your views here.

def home(request):
    return render(request,'Vote/home.html',context={'status':1})