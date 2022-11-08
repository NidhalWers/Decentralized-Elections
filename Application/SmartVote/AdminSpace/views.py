from django.shortcuts import render

# Create your views here.
def alpha(request):
    return render(request,'AdminSpace/Alpha.html')

def administrateur(request):
    return render(request,'AdminSpace/Administrateur.html')

def beta(request):
    return render(request,'AdminSpace/Beta.html')

def gamma(request):
    return render(request,'AdminSpace/Gamma.html')

def lamnda(request):
    return render(request,'AdminSpace/Lambda.html')

def epsilon(request):
    return render(request,'AdminSpace/Epsilon.html')

def zelda(request):
    return render(request,'AdminSpace/Zelda.html')

def parametre(request):
    return render(request,'AdminSpace/Parametre.html')