from rest_framework import routers,serializers,viewsets
from .models import Candidate

class CandidateSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Candidate
        fields = ['CandidateName','CandidateDescription','CandidateImage','CandidateProgram']
