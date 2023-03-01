from rest_framework import routers,serializers,viewsets
from .models import Candidate,Election

class CandidateSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Candidate
        fields = ['CandidateName','CandidateDescription','CandidateImage','CandidateProgram']

class ElectionSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Election
        fields = ['ElectionName','ElectionStatus','ElectionApiKey','ElectionCandidates','ElectionStartDate','ElectionEndDate','ElectionBlankVote','ElectionBlind']
