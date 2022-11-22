from django.db import models

class Candidate(models.Model):
    CandidateName = models.CharField(max_length=200,primary_key=True)
    CandidateDescription = models.CharField(max_length=200, blank=True , null=True)
    CandidateImage = models.ImageField(upload_to='CandidateImage', blank=True, null=True)
    CandidateProgram  = models.FileField(upload_to='CandidateProgram',blank=True , null=True)

class Election(models.Model):
    ElectionName = models.CharField(max_length=200)
    ElectionStatus = models.CharField(max_length=200, blank=True , null=True)
    ElectionApiKey = models.CharField(max_length=500, blank=True , null=True)
   