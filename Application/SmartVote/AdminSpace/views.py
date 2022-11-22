import os
from django.shortcuts import render
from AdminSpace.models import Candidate
from django.http import JsonResponse,HttpResponse
from rest_framework.parsers import JSONParser
# To bypass having a CSRF token
from django.views.decorators.csrf import csrf_exempt
# API definition for task
from .serializers import CandidateSerializer,ElectionSerializer
from rest_framework.decorators import api_view
from cryptography.fernet import Fernet
from dotenv import load_dotenv
load_dotenv('.env')

# Create your views here.
def administrateur(request):
    return render(request,'AdminSpace/Administrateur.html')

def parametre(request):
    return render(request,'AdminSpace/Parametre.html')

def index(request):
    return render(request,'AdminSpace/index.html')

def success(request):
    return render(request,'AdminSpace/success.html')

# Utils

def isCandidateExist(candidate):
    return Candidate.objects.filter(CandidateName=candidate).exists()

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
        candidate = Candidate.objects.all()
        # serialize the task data
        serializer = CandidateSerializer(candidate, many=True)
        # return a Json response
        return JsonResponse(serializer.data,safe=False)
    else:
        return HttpResponse(status=400)

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
            #delete file if new file is uploaded
            try:
                if(candidate.CandidateImage != request.data['CandidateImage']):
                    os.remove(candidate.CandidateImage.path)
            except:
                pass
            try:
                if(candidate.CandidateProgram != request.data['CandidateProgram']):
                    os.remove(candidate.CandidateProgram.path)
            except:
                pass

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
        if(serializer.is_valid()):
            # if okay, save it on the database
            serializer.save()
            # provide a Json Response with the data that was saved
            return JsonResponse(serializer.data, status=201)

        return JsonResponse(serializer.errors, status=400)
