from django.shortcuts import render

# Create your views here.

def connect(request):
    return render(request,'test.html')