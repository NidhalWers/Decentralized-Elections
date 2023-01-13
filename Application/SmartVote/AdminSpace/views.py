import os
from django.shortcuts import render,redirect
from AdminSpace.models import Candidate,Election
from django.http import JsonResponse,HttpResponse
# To bypass having a CSRF token
from django.views.decorators.csrf import csrf_exempt
# API definition for task
from .serializers import CandidateSerializer,ElectionSerializer
from rest_framework.decorators import api_view
from cryptography.fernet import Fernet
from dotenv import load_dotenv
load_dotenv('.env')
from Connect.models import Citizen
from django.contrib.auth import authenticate, login

# Create your views here.
def parametre(request):
    if request.user.is_authenticated:
        if request.user.is_superuser:
            return render(request,'AdminSpace/parametre.html')
    return redirect('/')

def index(request):
    if request.user.is_authenticated:
        if request.user.is_superuser:
            return render(request,'AdminSpace/index.html')
    return redirect('/')

def success(request):
    if request.user.is_authenticated:
        if request.user.is_superuser:
            return render(request,'AdminSpace/success.html')
    return redirect('/')

def connect_admin(request):
    if request.method == 'POST':
        id_connect = request.POST['email']
        password = request.POST['password1']
        user = authenticate(id_connect=id_connect, password=password)
        if user is not None:
            if user.is_active:
                login(request, user)
                return redirect('/', context={'status':1})
            else:
                return render (request,'AdminSpace/connect/sign-in.html', context={'status':0,'error':'Compte inactif ou inexistant'})
        else:
            return render (request,'AdminSpace/connect/sign-in.html', context={'status':0,'error':'Mot de passe ou identifiant incorrect'})
    else:
        if request.user.is_authenticated:
            return redirect('/', context={'status':1})
        else:
            return render(request,'AdminSpace/connect/sign-in.html', context={'status':1})

def viewElectionStatus(request,name,status):
    if request.user.is_authenticated:
        if request.user.is_superuser:
            return render(request,'AdminSpace/viewElection.html', context={'name':name,'status':status})
    return redirect('/')

def viewElection(request,name):
    if request.user.is_authenticated:
        if request.user.is_superuser:
            return render(request,'AdminSpace/viewElection.html', context={'name':name,'status':"None"})
    return redirect('/')

# Utils

def isCandidateExist(candidate):
    return Candidate.objects.filter(CandidateName=candidate).exists()

def isElectionExist(name,status='None'):
    if status=='None':
        return Election.objects.filter(ElectionName=name).exists()
    else:
        return Election.objects.filter(ElectionName=name,ElectionStatus=status).exists()

def encrypt(message: bytes, key: bytes) -> bytes:
    return Fernet(key).encrypt(message)

def decrypt(token: bytes, key: bytes) -> bytes:
    return Fernet(key).decrypt(token)

#Api
@api_view(['POST'])
def addCandidate(request):
    if(request.method == 'POST'):
        serializer = CandidateSerializer(data=request.data)
        if not(isCandidateExist(request.data['CandidateName'])):
            if(serializer.is_valid()):
                # if okay, save it on the database
                serializer.save()
                # provide a Json Response with the data that was saved
                return JsonResponse(serializer.data, status=201)
        else:
            return JsonResponse({'message':'Candidate already exist'}, status=400)

        return JsonResponse(serializer.errors, status=400)


def getCandidate(request, pk):
    if(request.method == 'GET'):
        try:
            candidate = Candidate.objects.get(pk=pk)
        except Candidate.DoesNotExist:
            return JsonResponse({'message':'Candidate does not exist'}, status=400)
        # serialize the tasks
        serializer = CandidateSerializer(candidate, many=False)
        # return the serialized tasks
        return JsonResponse(serializer.data, safe=False)

@csrf_exempt
def getCandidates(request):
    '''
    List all candidates snippets
    '''
    if(request.method == 'GET'):
        # get all the tasks
        candidate = Candidate.objects.all().order_by('CandidateName')
        # serialize the task data
        serializer = CandidateSerializer(candidate, many=True)
        # return a Json response
        return JsonResponse(serializer.data,safe=False)
    else:
        return HttpResponse(status=400)


def getElectionStatus(request,name,status='None'):
    '''
    List all candidates snippets
    '''
    if(request.method == 'GET'):
        # get all the tasks
        try:
            election = Election.objects.filter(ElectionName=name,ElectionStatus=status)
        except Election.DoesNotExist:
            return JsonResponse({'message':'Election does not exist'}, status=400)
        # serialize the task data
        serializer = ElectionSerializer(election, many=True)
        # return a Json response
        return JsonResponse(serializer.data,safe=False)

def getElection(request,name):
    '''
    List all candidates snippets
    '''
    if(request.method == 'GET'):
        try:
            # get all the tasks
            election = Election.objects.filter(ElectionName=name)
            # serialize the task data
            serializer = ElectionSerializer(election, many=True)
            # return a Json response
            return JsonResponse(serializer.data,safe=False)
        except Election.DoesNotExist:
            return JsonResponse({'message':'Election does not exist'}, status=400)

def getElections(request):
    '''
    List all candidates snippets
    '''
    if(request.method == 'GET'):
        # get all the tasks
        election = Election.objects.all().order_by('ElectionName')
        # serialize the task data
        serializer = ElectionSerializer(election, many=True)
        # return a Json response
        return JsonResponse(serializer.data,safe=False)

@api_view(['DELETE'])
def delCandidate(request, pk):
    '''
    Delete a candidate
    '''
    if(request.method == 'DELETE'):
        # get the task with the id
        try:
            candidate = Candidate.objects.get(CandidateName=pk)
        except Candidate.DoesNotExist:
            return HttpResponse({"message':'Candidate does'nt exist"},status=400)

        # check if is in an election
        print(Election.objects.all().values_list('ElectionCandidates',flat=True))
        for election in Election.objects.all().values_list('ElectionCandidates',flat=True):
            if pk in election:
                return JsonResponse({"message":"Candidate is in an election, delete election before deleting the candidate"},status=400)

        # delete files
        try:
            if(candidate.CandidateImage):
                os.remove(candidate.CandidateImage.path)
        except:
            pass
        try:
            if(candidate.CandidateProgram):
                os.remove(candidate.CandidateProgram.path)
        except:
            pass
        # delete candidate
        candidate.delete()
        # return a Json response
        return JsonResponse({'message':'Candidate deleted successfully'}, status=204)
    else:
        return HttpResponse(status=400)

@api_view(['PUT'])
def updateCandidate(request, pk):
    '''
    Update a candidate
    '''
    if(request.method == 'PUT'):
        # get the task with the id
        try:
            candidate = Candidate.objects.get(CandidateName=pk)
        except Candidate.DoesNotExist:
            return JsonResponse({"message':'Candidate does'nt exist"},status=400)
        
        serializer = CandidateSerializer(candidate,data=request.data)
            
        # check if the data is valid
        if(serializer.is_valid()):
            serializer.save()
            # #delete file if new file is uploaded
            # try:
            #     if(candidate.CandidateImage != request.data['CandidateImage']):
            #         os.remove(candidate.CandidateImage.path)
            # except:
            #     pass
            # try:
            #     if(candidate.CandidateProgram != request.data['CandidateProgram']):
            #         os.remove(candidate.CandidateProgram.path)
            # except:
            #     pass

            if(serializer.data["CandidateName"] != pk):
                print("name changed")
                try:
                    candidate = Candidate.objects.get(CandidateName=pk)
                    candidate.delete()
                except Candidate.DoesNotExist:
                    return HttpResponse({"message':'Error updating CandidateName : Candidate does'nt exist"},status=400)

            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)
    
@api_view(['POST'])
def addElection(request):
    if(request.method == 'POST'):
        request.data._mutable=True
        request.data['ElectionApiKey']= encrypt(request.data['ElectionApiKey'].encode(), os.environ.get("DECRYPT_KEY").encode())
        serializer = ElectionSerializer(data=request.data)
        if not(isElectionExist(request.data['ElectionName'],request.data['ElectionStatus'])):
            if(serializer.is_valid()):
                # if okay, save it on the database
                serializer.save()
                # provide a Json Response with the data that was saved
                return JsonResponse(serializer.data, status=201)
            else:
                 return JsonResponse(serializer.errors, status=400)
        else:
            return JsonResponse({'message':'Election already exist'}, status=400)
       
@api_view(['DELETE'])
def delElectionStatus(request, pk, status='None'):
    '''
    Delete a election
    '''
    if(request.method == 'DELETE'):
        # get the task with the id
        try:
            election = Election.objects.get(ElectionName=pk,ElectionStatus=status)
        except Election.DoesNotExist:
            return JsonResponse({"message':'Election does'nt exist"},status=400)

        # delete election
        election.delete()
        # return a Json response
        return JsonResponse({'message':'Election deleted successfully'}, status=204)
    else:
        return HttpResponse(status=400)

@api_view(['DELETE'])
def delElection(request, pk):
    '''
    Delete a election
    '''
    if(request.method == 'DELETE'):
        # get the task with the id
        try:
            election = Election.objects.get(ElectionName=pk)
        except Election.DoesNotExist:
            return JsonResponse({"message':'Election does'nt exist"},status=400)

        # delete election
        election.delete()
        # return a Json response
        return JsonResponse({'message':'Election deleted successfully'}, status=204)
    else:
        return HttpResponse(status=400)

@api_view(['PUT'])
def updateElection(request, pk):
    '''
    Update a election
    '''
    if(request.method == 'PUT'):
        # get the task with the id
        try:
            election = Election.objects.get(ElectionName=pk)
        except Election.DoesNotExist:
            return JsonResponse({"message':'Election does'nt exist"},status=400)
        
        serializer = ElectionSerializer(election,data=request.data)
            
        # check if the data is valid
        if(serializer.is_valid()):
            serializer.save()
            if(serializer.data["ElectionName"] != pk):
                print("name changed")
                try:
                    election = Election.objects.get(ElectionName=pk)
                    election.delete()
                except Election.DoesNotExist:
                    return HttpResponse({"message':'Error updating ElectionName : Election does'nt exist"},status=400)

            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@api_view(['PUT'])
def updateElectionStatus(request, pk, status='None'):
    '''
    Update a election
    '''
    if(request.method == 'PUT'):
        # get the task with the id
        try:
            election = Election.objects.get(ElectionName=pk,ElectionStatus=status)
        except Election.DoesNotExist:
            return JsonResponse({"message':'Election does'nt exist"},status=400)
        
        serializer = ElectionSerializer(election,data=request.data)
            
        # check if the data is valid
        if(serializer.is_valid()):
            serializer.save()
            if(serializer.data["ElectionName"] != pk):
                print("name changed")
                try:
                    election = Election.objects.get(ElectionName=pk)
                    election.delete()
                except Election.DoesNotExist:
                    return HttpResponse({"message':'Error updating ElectionName : Election does'nt exist"},status=400)

            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)