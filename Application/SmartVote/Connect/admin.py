from django.contrib import admin
from django.contrib.auth.admin import UserAdmin

from .models import Citizen
from import_export import resources
from import_export.admin import ImportExportModelAdmin
from import_export.admin import ExportMixin
from django.contrib.auth.hashers import make_password

class CitizenRessource(resources.ModelResource):
    def before_import_row(self,row, **kwargs):
           value = row['password']
           row['password'] = make_password(value)
           
    class Meta:
        model = Citizen
        skip_unchanged = True
        report_skipped = True
        # exclude = ('id')
        fields = ('id','username','password', 'email', 'first_name', 'last_name',
        'gender','city','phone','postal_code','street_name','street_number','fiscal_number','sick_security_number','birth_date')

class CitizenAdmin(UserAdmin,ImportExportModelAdmin,ExportMixin, admin.ModelAdmin):
    list_display = (
        'username', 'email', 'first_name', 'last_name', 'is_staff',
        'gender','city','phone','postal_code','street_name','street_number','fiscal_number','sick_security_number','birth_date'
        )

    fieldsets = (
        (None, {
            'fields': ('username', 'password')
        }),
        ('Personal info', {
            'fields': ('first_name', 'last_name', 'email')
        }),
        ('Permissions', {
            'fields': (
                'is_active', 'is_staff', 'is_superuser',
                'groups', 'user_permissions'
                )
        }),
        ('Important dates', {
            'fields': ('last_login', 'date_joined')
        }),
        ('Additional info', {
            'fields': ('gender','city','phone','postal_code','street_name','street_number','fiscal_number','sick_security_number','birth_date')
        })
    )

    add_fieldsets = (
        (None, {
            'fields': ('username', 'password1', 'password2')
        }),
        ('Personal info', {
            'fields': ('first_name', 'last_name', 'email')
        }),
        ('Permissions', {
            'fields': (
                'is_active', 'is_staff', 'is_superuser',
                'groups', 'user_permissions'
                )
        }),
        ('Important dates', {
            'fields': ('last_login', 'date_joined')
        }),
        ('Additional info', {
            'fields': ('gender','city','phone','postal_code','street_name','street_number','fiscal_number','sick_security_number','birth_date')
        })
    )

    resource_class = CitizenRessource
    # Other admin definition here
    list_display = ('username', 'email', 'first_name', 'last_name', 'is_staff',
        'gender','city','phone','postal_code','street_name','street_number','fiscal_number','sick_security_number','birth_date')



admin.site.register(Citizen, CitizenAdmin)
