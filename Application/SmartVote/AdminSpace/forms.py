from django import forms
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from Connect.models import Citizen

class NewUserForm(UserCreationForm):
    class Meta:
        model = Citizen
        fields = ("email","password1")

    def save(self, commit=True):
        user = super(NewUserForm, self).save(commit=False)
        if commit:
            user.save()
        return user
