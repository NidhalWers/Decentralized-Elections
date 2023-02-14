from django.shortcuts import render
import os
from django.shortcuts import render,redirect
from .models import CandidateLite,ElectionLite
from django.http import JsonResponse,HttpResponse
# To bypass having a CSRF token
from django.views.decorators.csrf import csrf_exempt
# API definition for task
from .serializers import ElectionLiteSerializer,CandidateLiteSerializer
from rest_framework.decorators import api_view
from cryptography.fernet import Fernet
from dotenv import load_dotenv
load_dotenv('.env')
from Connect.models import Citizen
from django.contrib.auth import authenticate, login
import random
import string
import smtplib
from email.mime.text import MIMEText

# Create your views here.
def home(request):
    return render(request,'SmartVoteLite/home.html')

# Create your views here.
def parametre(request):
    return render(request,'SmartVoteLite/parametre.html')

def success(request,code):
    return render(request,'SmartVoteLite/success.html',context={'code':code})

def viewElection(request,code):
    return render(request,'SmartVoteLite/viewElection.html', context={'code':code})

def resultElection(request,code):
    return render(request,'SmartVoteLite/result.html', context={'code':code})

def verification(request,code):
    if request.method == 'GET':
        try:
            codeSent = code
            if(codeSent == request.session['codeVerification']):
                print(request.session['codeElection'])
                # request.session = request.session.pop('codeVerification', None)
                request.session['codeVerification'] = None
                request.session['Allowed'] = True
                return JsonResponse({'message':'Code valide','ElectionCode':request.session['codeElection']}, status=200)
            else:
                return JsonResponse({'message':'Code invalide'}, status=400)
        except Exception as e:
            return JsonResponse({'message':e}, status=400)
    else:
        return render(request,'SmartVoteLite/verification.html')

def enterCode(request):
    if request.method == 'POST':
        email = request.POST['email']
        code = request.POST['code']
        try:
            election = ElectionLite.objects.get(ElectionCode=code)
            try:
                codeVerification = sendEmailConfirmation(email)
                request.session['codeVerification'] = codeVerification
            except:
                return render(request,'SmartVoteLite/enterCode.html',context={'status':0,'error':'Erreur lors de l\'envoi du code'})
        except:
            return render(request,'SmartVoteLite/enterCode.html',context={'status':0,'error':'Code invalide'})
        
        request.session['codeElection'] = code
        return render(request,'SmartVoteLite/verification.html',context={'status':1,'error':'Code envoyé'})
    else:
        return render(request,'SmartVoteLite/enterCode.html')

# Utils

def sendEmailConfirmation(receiver):
    code = random.randint(100000,999999)
    sender_email = os.environ.get("EMAIL")
    sender_password = os.environ.get("EMAIL_KEY")
    recipient_email = receiver
    subject = "Smart Vote - Code de vérification"
    body = """
    <h1>Bonjour,</h1>

    <p>Vous avez demandé à voter sur SmartVote. Veuillez entrer le code de vérification suivant afin d'avoir accès à l'élection smartvote:</p>

    <h2>"""+str(code)+"""</h2>

    <p>
    Ce code n'est valide que pour cette élection et n'inclut aucune inscription à notre service. Il ne sert qu'à vérifier que vous êtes bien le propriétaire de l'adresse e-mail que vous avez fournie afin de vous permettre de voter.
    </p>

    <p>
    Si vous rencontrez des problèmes ou avez des questions, n'hésitez pas à nous contacter. Nous sommes là pour vous aider.
    </p>

    <p>
    Merci d'avoir choisi notre service.
    </p>

    <p>
    --------------------------
    </p>

    <p>
    SmartoVote: https://smartvote.com    
    </p>     

    """
    html_message = MIMEText(body, 'html')
    html_message['Subject'] = subject
    html_message['From'] = sender_email
    html_message['To'] = recipient_email
    server = smtplib.SMTP_SSL('smtp.gmail.com', 465)
    server.login(sender_email, sender_password)
    server.sendmail(sender_email, recipient_email, html_message.as_string())
    server.quit()


    return code

def isCandidateExistInElection(candidate,election):
    return CandidateLite.objects.filter(CandidateName=candidate,CandidateElection=election).exists()

def isElectionExist(name,status='None'):
    if status=='None':
        return ElectionLite.objects.filter(ElectionName=name).exists()
    else:
        return ElectionLite.objects.filter(ElectionName=name,ElectionStatus=status).exists()

def encrypt(message: bytes, key: bytes) -> bytes:
    return Fernet(key).encrypt(message)

def decrypt(token: bytes, key: bytes) -> bytes:
    return Fernet(key).decrypt(token)

def generate_random_string(length=8):
    letters = string.ascii_letters + string.digits
    return ''.join(random.choice(letters) for i in range(length))

#Api
@api_view(['POST'])
def addCandidate(request):
    if(request.method == 'POST'):
        try:
            serializer = CandidateLiteSerializer(data=request.data)
            if not(isCandidateExistInElection(request.data['CandidateName'],request.data['CandidateElection'])):
                if(serializer.is_valid()):
                    # if okay, save it on the database
                    serializer.save()
                    # provide a Json Response with the data that was saved
                    return JsonResponse(serializer.data, status=201)
                else:
                    return JsonResponse(serializer.errors, status=400)
            else:
                return JsonResponse({'message':'Ce candidat existe déjà'}, status=400)
        except Exception as e:
            return JsonResponse(serializer.errors, status=400)

def getCandidate(request, pk):
    if(request.method == 'GET'):
        try:
            candidate = CandidateLite.objects.get(pk=pk)
        except CandidateLite.DoesNotExist:
            return JsonResponse({'message':"Ce candidat n'existe pas"}, status=400)
        # serialize the tasks
        serializer = CandidateLiteSerializer(candidate, many=False)
        # return the serialized tasks
        return JsonResponse(serializer.data, safe=False)

@csrf_exempt
def getCandidatesInElection(request,election):
    '''
    List all candidates snippets
    '''
    if(request.method == 'GET'):
        # get all the tasks
        candidate = CandidateLite.objects.filter(CandidateElection=election).order_by('CandidateName')
        # serialize the task data
        serializer = CandidateLiteSerializer(candidate, many=True)
        # return a Json response
        return JsonResponse(serializer.data,safe=False)
    else:
        return HttpResponse(status=400)

def getElection(request,code):
    '''
    List all candidates snippets
    '''
    if(request.method == 'GET'):
        try:
            # get all the tasks
            election = ElectionLite.objects.filter(ElectionCode=code)
            # serialize the task data
            serializer = ElectionLiteSerializer(election, many=True)
            # return a Json response
            return JsonResponse(serializer.data,safe=False)
        except ElectionLite.DoesNotExist:
            return JsonResponse({'message':"Cette élection n'existe pas"}, status=400)

def getElections(request):
    '''
    List all candidates snippets
    '''
    if(request.method == 'GET'):
        # get all the tasks
        election = ElectionLite.objects.all().order_by('ElectionName')
        # serialize the task data
        serializer = ElectionLiteSerializer(election, many=True)
        # return a Json response
        return JsonResponse(serializer.data,safe=False)

@api_view(['DELETE'])
def delCandidate(request, pk,election):
    '''
    Delete a candidate
    '''
    if(request.method == 'DELETE'):
        # get the task with the id
        try:
            candidate = CandidateLite.objects.get(CandidateName=pk,CandidateElection=election)
        except CandidateLite.DoesNotExist:
            return HttpResponse({'message':"Ce candidat n'existe pas"},status=400)

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
        return JsonResponse({'message':'CandidateLite deleted successfully'}, status=204)
    else:
        return HttpResponse(status=400)

@api_view(['PUT'])
def updateCandidate(request, pk,election):
    '''
    Update a candidate
    '''
    if(request.method == 'PUT'):
        # get the task with the id
        try:
            candidate = CandidateLite.objects.get(CandidateName=pk,CandidateElection=election)
        except CandidateLite.DoesNotExist:
            return JsonResponse({'message':"Ce candidat n'existe pas"},status=400)
        
        for election in ElectionLite.objects.all().values_list('ElectionCandidates',flat=True):
            if pk in election:
                return JsonResponse({"message":"CandidateLite is in an election, delete election before update the candidate"},status=400)

        serializer = CandidateLiteSerializer(candidate,data=request.data)
            
        # check if the data is valid
        if(serializer.is_valid()):
            try:
                print("request.data['CandidateImage'] : ")
                print(request.data['CandidateImage'])
            except:
                pass
            try:
                print("candidate.CandidateImage : ")
                print(candidate.CandidateImage)
            except:
                pass

            try :
                if request.data['CandidateImage']:
                    if(candidate.CandidateImage != request.data['CandidateImage']):
                        try:
                            os.remove(candidate.CandidateImage.path)
                        except:
                            pass
                else:
                    try:
                        os.remove(candidate.CandidateImage.path)
                    except:
                        pass
            except:
                pass

            try :
                if request.data['CandidateProgram']:
                    if(candidate.CandidateProgram != request.data['CandidateProgram']):
                        try:
                            os.remove(candidate.CandidateProgram.path)
                        except:
                            pass
                else:
                    try:
                        os.remove(candidate.CandidateProgram.path)
                    except:
                        pass
            except:
                pass

            serializer.save()

            if(serializer.data["CandidateName"] != pk):
                print("name changed")
                try:
                    candidate = CandidateLite.objects.get(CandidateName=pk)
                    candidate.delete()
                except CandidateLite.DoesNotExist:
                    return HttpResponse({'message':"Ce candidat n'existe pas"},status=400)

            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)
    
@api_view(['POST'])
def addElection(request):
    if(request.method == 'POST'):
        request.data._mutable=True
        request.data['ElectionApiKey']= encrypt(request.data['ElectionApiKey'].encode(), os.environ.get("DECRYPT_KEY").encode())
        serializer = ElectionLiteSerializer(data=request.data)
        if not(isElectionExist(request.data['ElectionName'],request.data['ElectionStatus'])):
            if(serializer.is_valid()):
                # if okay, save it on the database
                serializer.save()
                # provide a Json Response with the data that was saved
                return JsonResponse(serializer.data, status=201)
            else:
                 return JsonResponse(serializer.errors, status=400)
        else:
            return JsonResponse({'message':'Cette élection existe déjà'}, status=400)
       
@api_view(['DELETE'])
def delElectionStatus(request, pk, status='None'):
    '''
    Delete a election
    '''
    if(request.method == 'DELETE'):
        # get the task with the id
        try:
            election = ElectionLite.objects.get(ElectionName=pk,ElectionStatus=status)
        except ElectionLite.DoesNotExist:
            return JsonResponse({'message':"Cette élection n'existe pas"},status=400)

        # delete election
        election.delete()
        # return a Json response
        return JsonResponse({'message':'ElectionLite deleted successfully'}, status=204)
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
            election = ElectionLite.objects.get(ElectionName=pk,ElectionStatus="")
        except ElectionLite.DoesNotExist:
            return JsonResponse({'message':"Cette élection n'existe pas"},status=400)

        # delete election
        election.delete()
        # return a Json response
        return JsonResponse({'message':'ElectionLite deleted successfully'}, status=204)
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
            election = ElectionLite.objects.get(ElectionName=pk)
        except ElectionLite.DoesNotExist:
            return JsonResponse({'message':"Cette élection n'existe pas"},status=400)
        
        serializer = ElectionLiteSerializer(election,data=request.data)
            
        # check if the data is valid
        if(serializer.is_valid()):
            serializer.save()
            if(serializer.data["ElectionName"] != pk):
                print("name changed")
                try:
                    election = ElectionLite.objects.get(ElectionName=pk)
                    election.delete()
                except ElectionLite.DoesNotExist:
                    return HttpResponse({'message':"Cette élection n'existe pas"},status=400)

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
            election = ElectionLite.objects.get(ElectionName=pk,ElectionStatus=status)
        except ElectionLite.DoesNotExist:
            return JsonResponse({'message':"Cette élection n'existe pas"},status=400)
        
        serializer = ElectionLiteSerializer(election,data=request.data)
            
        # check if the data is valid
        if(serializer.is_valid()):
            serializer.save()
            if(serializer.data["ElectionName"] != pk):
                print("name changed")
                try:
                    election = ElectionLite.objects.get(ElectionName=pk)
                    election.delete()
                except ElectionLite.DoesNotExist:
                    return HttpResponse({'message':"Cette élection n'existe pas"},status=400)

            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@api_view(['GET'])
def isElectionExistStatusAPI(request, name, status):
    '''
    Check if an election exist
    '''
    if(request.method == 'GET'):
        # get the task with the id
        try:
            election = ElectionLite.objects.get(ElectionName=name,ElectionStatus=status)
        except ElectionLite.DoesNotExist:
            return JsonResponse({'message':"Cette élection n'existe pas"},status=201)
        return JsonResponse({'message':'Cette élection existe déjà'}, status=201)

@api_view(['GET'])
def isElectionExistAPI(request, name):
    '''
    Check if an election exist
    '''
    if(request.method == 'GET'):
        # get the task with the id
        try:
            election = ElectionLite.objects.get(ElectionName=name,ElectionStatus="")
        except ElectionLite.DoesNotExist:
            return JsonResponse({'message':"Cette élection n'existe pas"},status=201)
        return JsonResponse({'message':'Cette élection existe déjà'}, status=201)