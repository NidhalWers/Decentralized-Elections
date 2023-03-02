from django.contrib import admin
from django.contrib.auth.models import User
from import_export import resources

from import_export import resources
from import_export.admin import ExportMixin
from django.contrib.auth.models import User
from django.contrib.auth.admin import UserAdmin
from import_export.admin import ImportExportModelAdmin

from .models import CandidateLite, ElectionLite
# Register your models here.

class CandidateRessource(resources.ModelResource):
    class Meta:
        model = CandidateLite
        fields = ('CandidateName','CandidateDescription','CandidateImage','CandidateProgram','CandidateElection')

class CandidateAdmin(ImportExportModelAdmin,ExportMixin, admin.ModelAdmin):
    resource_class = CandidateRessource
    # Other admin definition here
    list_display = ('CandidateName','CandidateDescription','CandidateImage','CandidateProgram','CandidateElection')
    pass

class ElectionRessource(resources.ModelResource):
    class Meta:
        model = ElectionLite
        fields = ('ElectionName','ElectionCandidates','ElectionStatus','ElectionApiKey','ElectionStartDate','ElectionEndDate','ElectionBlankVote','ElectionCode')

class ElectionAdmin(ImportExportModelAdmin,ExportMixin, admin.ModelAdmin):
    resource_class = ElectionRessource
    # Other admin definition here
    list_display = ('ElectionName','ElectionCandidates','ElectionStatus','ElectionApiKey','ElectionStartDate','ElectionEndDate','ElectionBlankVote','ElectionCode')
    pass

admin.site.register(ElectionLite, ElectionAdmin)
admin.site.register(CandidateLite, CandidateAdmin)
