from rest_framework import routers,serializers,viewsets
from .models import Candidate
class CandidateSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Candidate
        fields = ['CandidateName','CandidateDescription','CandidateImage','CandidateProgram']

    def get_photo_url(self, Candidate):
        request = self.context.get('request')
        photo_url = Candidate.photo.url
        return request.build_absolute_uri(photo_url)