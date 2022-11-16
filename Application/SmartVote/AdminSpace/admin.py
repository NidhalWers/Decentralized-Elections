from django.contrib import admin
from django.contrib.auth.models import User
from import_export import resources

from import_export import resources
from import_export.admin import ExportMixin
from django.contrib.auth.models import User
from django.contrib.auth.admin import UserAdmin
from import_export.admin import ImportExportModelAdmin

from .models import Candidate

class CandidateRessource(resources.ModelResource):
    class Meta:
        model = Candidate
        fields = ('CandidateName','CandidateDescription','CandidateImage','CandidateProgram')

class CandidateAdmin(ImportExportModelAdmin,ExportMixin, admin.ModelAdmin):
    resource_class = CandidateRessource
    # Other admin definition here
    list_display = ('CandidateName','CandidateDescription','CandidateImage','CandidateProgram')
    pass

admin.site.register(Candidate, CandidateAdmin)