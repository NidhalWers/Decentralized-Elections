from django.shortcuts import render

# Create your views here.
from django.shortcuts import render

from Connect.models import Citizen

# Create your views here.

def vote(request):
    if request.user.is_authenticated:
        return render(request,'Vote/vote.html',context={'status':1})
    else:
        return render(request,'Vote/home.html',context={'status':1})

def selectVote(request):
    if request.user.is_authenticated:
        return render(request,'Vote/selectVote.html',context={'status':1})
    else:
        return render(request,'Vote/home.html',context={'status':1})