from django.shortcuts import render
from AdminSpace.models import Candidate
from django.http import JsonResponse,HttpResponse
from rest_framework.parsers import JSONParser
# To bypass having a CSRF token
from django.views.decorators.csrf import csrf_exempt
# API definition for task
from .serializers import CandidateSerializer
from rest_framework.decorators import api_view

# Create your views here.
def administrateur(request):
    return render(request,'AdminSpace/Administrateur.html')

def parametre(request):
    return render(request,'AdminSpace/Parametre.html')

def index(request):
    return render(request,'AdminSpace/index.html')

#api
@api_view(['POST'])
def addCandidate(request):
    if(request.method == 'POST'):
        # data = JSONParser().parse(request)
        serializer = CandidateSerializer(data=request.data)
        # instanciate with the serializer
        # serializer = CandidateSerializer(data=data)
        # check if the sent information is okay
        if(serializer.is_valid()):
            # if okay, save it on the database
            serializer.save()
            # provide a Json Response with the data that was saved
            return JsonResponse(serializer.data, status=201)
            # provide a Json Response with the necessary error information
        print(serializer.errors)
        return JsonResponse(serializer.errors, status=400)

@csrf_exempt
def candidate(request):
    '''
    List all candidates snippets
    '''
    if(request.method == 'GET'):
        # get all the tasks
        tasks = Candidate.objects.all()
        # serialize the task data
        serializer = CandidateSerializer(tasks, many=True)
        # return a Json response
        return JsonResponse(serializer.data,safe=False)
    
@csrf_exempt
def candidate_detail(request, pk):
    try:
        # obtain the task with the passed id.
        task = Candidate.objects.get(pk=pk)
    except:
        # respond with a 404 error message
        return HttpResponse(status=404)  
    if(request.method == 'PUT'):
        # parse the incoming information
        data = JSONParser().parse(request)  
        # instanciate with the serializer
        serializer = CandidateSerializer(task, data=data)
        # check whether the sent information is okay
        if(serializer.is_valid()):  
            # if okay, save it on the database
            serializer.save() 
            # provide a JSON response with the data that was submitted
            return JsonResponse(serializer.data, status=201)
        # provide a JSON response with the necessary error information
        return JsonResponse(serializer.errors, status=400)
    elif(request.method == 'DELETE'):
        # delete the task
        task.delete() 
        # return a no content response.
        return HttpResponse(status=204) 

# def addCandidate(request):
#     candidate = Candidate.objects.get(CandidateName = request.POST['CandidateName'])
#     if candidate is None :
#         try:
#             candidate = Candidate(CandidateName = CandidateName, CandidateDescription = CandidateDescription, CandidateImage = CandidateImage, CandidateProgram = CandidateProgram)
#             candidate.save()
#         except Exception as e:
#             return JsonResponse({'error': '1', 'message': str(e)})
#         return JsonResponse({'error' : "0", 'message' : "Candidate added successfully"})
#     else:
#         return JsonResponse({'error' : "1","message" : "Candidate already exist"})

# def getCandidate(request):
#     try:
#         candidates = list(Candidate.objects.all().values())
#     except Exception as e:
#         return JsonResponse({'error': '1', 'message': str(e)})
#     return JsonResponse({'error' : "0", 'message' : "Candidate found successfully", 'Candidates' : candidates, 'count' : len(candidates)})