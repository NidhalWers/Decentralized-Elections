from django.contrib import admin
from django.contrib.auth.models import User
from import_export import resources

from import_export import resources
from import_export.admin import ExportMixin
from django.contrib.auth.models import User
from django.contrib.auth.admin import UserAdmin
from import_export.admin import ImportExportModelAdmin

from .models import Candidate, Election

class CandidateRessource(resources.ModelResource):
    class Meta:
        model = Candidate
        fields = ('CandidateName','CandidateDescription','CandidateImage','CandidateProgram')

class CandidateAdmin(ImportExportModelAdmin,ExportMixin, admin.ModelAdmin):
    resource_class = CandidateRessource
    # Other admin definition here
    list_display = ('CandidateName','CandidateDescription','CandidateImage','CandidateProgram')
    pass

class ElectionRessource(resources.ModelResource):
    class Meta:
        model = Election
        fields = ('ElectionName','ElectionCandidates','ElectionStatus','ElectionApiKey','ElectionStartDate','ElectionEndDate','ElectionBlankVote','ElectionBlind')

class ElectionAdmin(ImportExportModelAdmin,ExportMixin, admin.ModelAdmin):
    resource_class = ElectionRessource
    # Other admin definition here
    list_display = ('ElectionName','ElectionCandidates','ElectionStatus','ElectionApiKey','ElectionStartDate','ElectionEndDate','ElectionBlankVote','ElectionBlind')
    pass

# Register your models here.
admin.site.register(Election, ElectionAdmin)
admin.site.register(Candidate, CandidateAdmin)
