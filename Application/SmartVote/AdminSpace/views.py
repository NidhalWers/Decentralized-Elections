from django.shortcuts import render

# Create your views here.
def adminspace(request):
    return render(request,'test.html')