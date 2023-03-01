from django.shortcuts import render,redirect
from django.contrib.auth import authenticate, login
from django.http import JsonResponse
from .models import Citizen
from .serializers import CitizenSerializer
from cryptography.fernet import Fernet
from dotenv import load_dotenv
load_dotenv('.env')
import os
import json
import hashlib
import base64

# Create your views here.

def fc_html(request):
    if not request.user.is_authenticated:
        return render(request,'Connect/fc.html')
    else :
        return render(request,'Vote/home.html',context={'status':1})

def impots_html(request):
    return render(request,'Connect/impots.html')

def ameli_html(request):
    return render(request,'Connect/ameli.html')

def account_html(request):
    return render(request,'Connect/account.html')

# Render 

def login_impots(request):
    if request.method == 'POST':
        id_connect = request.POST['fiscal_number']
        password = request.POST['password1']
        user = authenticate(id_connect=id_connect, password=password)
        if user is not None:
            if user.is_active:
                login(request, user)
                return redirect('/', context={'status':1})
            else:
                return render (request,'connect/impots.html', context={'status':0,'error':'Compte inactif ou inexistant'})
        else:
            return render (request,'connect/impots.html', context={'status':0,'error':'Mot de passe ou identifiant incorrect'})
    else:
        if request.user.is_authenticated:
            return render(request,'Vote/home.html',context={'status':1})
        else:
            return render(request,'connect/impots.html', context={'status':1})



def login_ameli(request):
    if request.method == 'POST':
        id_connect = request.POST['sick_security_number']
        password = request.POST['password1']
        user = authenticate(id_connect=id_connect, password=password)
        if user is not None:
            if user.is_active:
                login(request, user)
                return redirect('/', context={'status':1})
            else:
                return render (request,'connect/ameli.html', context={'status':0,'error':'Compte inactif ou inexistant'})
        else:
            return render (request,'connect/ameli.html', context={'status':0,'error':'Mot de passe ou identifiant incorrect'})
    else:
        if request.user.is_authenticated:
            return render(request,'Vote/home.html',context={'status':1})
        else:
            return render(request,'connect/ameli.html', context={'status':1})

# Utils 

def num_to_random_string(num):
    # Convert number to bytes
    num_bytes = str(num).encode('utf-8')

    # Generate hash value
    hash_object = hashlib.sha256(num_bytes)
    hash_bytes = hash_object.digest()

    # Encode hash value as base64 string
    random_string = base64.b64encode(hash_bytes).decode('utf-8')

    return random_string

# API

# def get_user_infos(request):
#     if request.method == 'GET':
#         if request.user.is_authenticated:
#             try:
#                 citizen = Citizen.objects.get(user=request.user)
#             except Citizen.DoesNotExist:
#                 return JsonResponse({'message':'User does not exist'}, status=400)
#             # serialize the tasks
#             serializer = CitizenSerializer(citizen, many=False)

#             # return the serialized tasks
#             return JsonResponse(serializer.data, safe=False)
#         else:
#             return JsonResponse({'status':0,'error':'Utilisateur non connecté'})

def get_user_infos(request,pk):
    if request.method == 'GET':
        if request.user.is_authenticated:
            try:
                citizen = Citizen.objects.get(pk=pk)
            except Citizen.DoesNotExist:
                return JsonResponse({'message':'User does not exist'}, status=400)
            # serialize the tasks
            serializer = CitizenSerializer(citizen, many=False)

            # return the serialized tasks
            return JsonResponse(serializer.data, safe=False)
        else:
            return JsonResponse({'status':0,'error':'Utilisateur non connecté'})

def get_user_id_crypted(request,pk):
    if request.method == 'GET':
        if request.user.is_authenticated:
            try:
                return JsonResponse(num_to_random_string(pk), safe=False)
            except Exception as e:
                print(e)
                return JsonResponse({'status':0,'error':e})
        else:
            return JsonResponse({'status':0,'error':'Utilisateur non connecté'})