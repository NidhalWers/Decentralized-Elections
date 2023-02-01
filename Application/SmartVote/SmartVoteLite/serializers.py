from rest_framework import routers,serializers,viewsets
from .models import ElectionLite,CandidateLite

class CandidateLiteSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CandidateLite
        fields = ['CandidateName','CandidateDescription','CandidateImage','CandidateProgram']

class ElectionLiteSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = ElectionLite
        fields = ['ElectionName','ElectionStatus','ElectionApiKey','ElectionCandidates','ElectionStartDate','ElectionEndDate','ElectionBlankVote','ElectionBlind','ElectionCode','ElectionCodeAdmin']
