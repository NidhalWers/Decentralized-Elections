from django.contrib.auth.models import User
from django.shortcuts import render,redirect
from django.contrib import auth
from django.shortcuts import render,redirect
from django.contrib.auth import authenticate, login

from Connect.models import Citizen

# Create your views here.

def login_html(request):
    return render(request,'Connect/login.html',context={'status':1})

def fc_html(request):
    if not request.user.is_authenticated:
        return render(request,'Connect/fc.html')
    else :
        return render(request,'Connect/login.html',context={'status':1})

def impots_html(request):
    return render(request,'Connect/impots.html')

def ameli_html(request):
    return render(request,'Connect/ameli.html')

# Render 

# def login_impots(request):
#     if request.method == 'POST':
#         print("in login")

#         user = auth.authenticate(fiscal_number=request.POST['fiscal_number'], password = request.POST['password1'])

#         if user is not None:
#             auth.login(request,user)
#             print("Logged")
#             return redirect('/connect/login')
#         else:
#             print("mdp incorrect")
#             return render (request,'connect/impots.html', context={'status':0})
#     else:
#         return render(request,'connect/impots.html', context={'status':1})

def login_impots(request):
    if request.method == 'POST':
        id_connect = request.POST['fiscal_number']
        password = request.POST['password1']
        user = authenticate(id_connect=id_connect, password=password)
        if user is not None:
            if user.is_active:
                login(request, user)
                return redirect('/connect/login', context={'status':1})
            else:
                return render (request,'connect/impots.html', context={'status':0,'error':'Compte inactif ou inexistant'})
        else:
            return render (request,'connect/impots.html', context={'status':0,'error':'Mot de passe ou identifiant incorrect'})
    else:
        if request.user.is_authenticated:
            return render(request,'connect/login.html',context={'status':1})
        else:
            return render(request,'connect/impots.html', context={'status':1})

# API