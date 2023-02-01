from django.db import models

# Create your models here.

class ElectionLite(models.Model):
    ElectionName = models.CharField(max_length=200)
    ElectionCandidates = models.CharField(max_length=10000, blank=True , null=True)
    ElectionStatus = models.CharField(max_length=200, blank=True , null=True)
    ElectionApiKey = models.CharField(max_length=500, blank=True , null=True)
    ElectionStartDate = models.DateTimeField(auto_now_add=False, blank=True, null=True)
    ElectionEndDate = models.DateTimeField(auto_now_add=False, blank=True, null=True)
    ElectionBlankVote = models.BooleanField(default=False)
    ElectionBlind = models.BooleanField(default=False)
    ElectionCode = models.CharField(max_length=200, blank=True , null=True)
    ElectionCodeModification = models.CharField(max_length=200, blank=True , null=True)
