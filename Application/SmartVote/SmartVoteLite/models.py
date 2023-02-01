from django.db import models

# Create your models here.
class CandidateLite(models.Model):
    CandidateName = models.CharField(max_length=200)
    CandidateDescription = models.CharField(max_length=200, blank=True , null=True)
    CandidateImage = models.ImageField(upload_to='CandidateImage', blank=True, null=True)
    CandidateProgram  = models.FileField(upload_to='CandidateProgram',blank=True , null=True)
    CandidateElection  = models.CharField(max_length=200)

class ElectionLite(models.Model):
    ElectionName = models.CharField(max_length=200)
    ElectionCandidates = models.CharField(max_length=10000, blank=True , null=True)
    ElectionStatus = models.CharField(max_length=200, blank=True , null=True)
    ElectionApiKey = models.CharField(max_length=500, blank=True , null=True)
    ElectionStartDate = models.DateTimeField(auto_now_add=False, blank=True, null=True)
    ElectionEndDate = models.DateTimeField(auto_now_add=False, blank=True, null=True)
    ElectionBlankVote = models.BooleanField(default=False)
    ElectionCode = models.CharField(max_length=200,primary_key=True)
