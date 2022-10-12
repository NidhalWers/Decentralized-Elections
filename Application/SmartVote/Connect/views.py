from django.shortcuts import render

# Create your views here.

def login(request):
    return render(request,'Connect/login.html')

def fc(request):
    return render(request,'Connect/fc.html')