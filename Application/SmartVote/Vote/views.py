from django.shortcuts import render,redirect

from Connect.models import Citizen

# Create your views here.

def vote(request):
    if request.user.is_authenticated:
        if request.user.is_superuser:
            return redirect('/adminspace')
        else:
            return render(request,'Vote/vote.html')
    else:
        return redirect('/')

def selectVote(request):
    if request.user.is_authenticated:
        if request.user.is_superuser:
            return redirect('/adminspace')
        else:
            return render(request,'Vote/selectVote.html',context={'status':1})
    else:
        return render(request,'Vote/home.html',context={'status':1})