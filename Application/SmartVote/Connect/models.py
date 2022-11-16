from django.db import models

# Create your models here.
from django.contrib.auth.models import AbstractUser

class Citizen(AbstractUser):
    gender = models.CharField(max_length=50, blank=True)
    city = models.TextField(max_length=500, blank=True)
    phone = models.TextField(null=True, blank=True)
    postal_code = models.IntegerField(null=True, blank=True)
    street_name = models.TextField(null=True, blank=True)
    street_number = models.IntegerField(null=True, blank=True)
    fiscal_number = models.BigIntegerField(blank=True,null=True)
    sick_security_number = models.BigIntegerField(blank=True,null=True)
    birth_date = models.DateField(blank=True,null=True)