from django import forms
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User

class NewUserForm(UserCreationForm):
    class Meta:
        model = User
        fields = ("fiscal_number","password1")

    

    def save(self, commit=True):
        user = super(NewUserForm, self).save(commit=False)
        if commit:
            user.save()
        return user


class NewUserFormAmeli(UserCreationForm):
    class Meta:
        model = User
        fields = ("sick_security_number","password1")

    

    def save(self, commit=True):
        user = super(NewUserForm, self).save(commit=False)
        if commit:
            user.save()
        return user