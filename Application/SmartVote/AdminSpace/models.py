from django.db import models

# Create your models here.
class ImageField(models.ImageField):
    def value_to_string(self, obj): # obj is Model instance, in this case, obj is 'Class'
        return obj.fig.url # not return self.url


class Candidate(models.Model):
    CandidateName = models.CharField(max_length=200)
    CandidateDescription = models.CharField(max_length=200, blank=True)
    CandidateImage = ImageField(upload_to='CandidateImage', blank=True)  
    CandidateProgram  = models.FileField(upload_to='CandidateProgram',blank=True)

    def __str__(self):
        return repr(self,"CandidateName")