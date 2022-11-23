from django.db import models
from django.db.models import IntegerField
from django.contrib.postgres.fields import ArrayField

class Candidate(models.Model):
    CandidateName = models.CharField(max_length=200,primary_key=True)
    CandidateDescription = models.CharField(max_length=200, blank=True , null=True)
    CandidateImage = models.ImageField(upload_to='CandidateImage', blank=True, null=True)
    CandidateProgram  = models.FileField(upload_to='CandidateProgram',blank=True , null=True)

class Election(models.Model):
    ElectionName = models.CharField(max_length=200)
    ElectionCandidates = models.CharField(max_length=10000, blank=True , null=True)
    ElectionStatus = models.CharField(max_length=200, blank=True , null=True)
    ElectionApiKey = models.CharField(max_length=500, blank=True , null=True)
    ElectionStartDate = models.DateTimeField(auto_now_add=False, blank=True, null=True)
    ElectionEndDate = models.DateTimeField(auto_now_add=False, blank=True, null=True)