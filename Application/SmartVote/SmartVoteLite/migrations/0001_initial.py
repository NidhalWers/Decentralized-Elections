# Generated by Django 4.1.2 on 2023-02-01 15:17

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = []

    operations = [
        migrations.CreateModel(
            name="CandidateLite",
            fields=[
                (
                    "id",
                    models.BigAutoField(
                        auto_created=True,
                        primary_key=True,
                        serialize=False,
                        verbose_name="ID",
                    ),
                ),
                ("CandidateName", models.CharField(max_length=200)),
                (
                    "CandidateDescription",
                    models.CharField(blank=True, max_length=200, null=True),
                ),
                (
                    "CandidateImage",
                    models.ImageField(
                        blank=True, null=True, upload_to="CandidateImage"
                    ),
                ),
                (
                    "CandidateProgram",
                    models.FileField(
                        blank=True, null=True, upload_to="CandidateProgram"
                    ),
                ),
                ("CandidateElection", models.CharField(max_length=200)),
            ],
        ),
        migrations.CreateModel(
            name="ElectionLite",
            fields=[
                ("ElectionName", models.CharField(max_length=200)),
                (
                    "ElectionCandidates",
                    models.CharField(blank=True, max_length=10000, null=True),
                ),
                (
                    "ElectionStatus",
                    models.CharField(blank=True, max_length=200, null=True),
                ),
                (
                    "ElectionApiKey",
                    models.CharField(blank=True, max_length=500, null=True),
                ),
                ("ElectionStartDate", models.DateTimeField(blank=True, null=True)),
                ("ElectionEndDate", models.DateTimeField(blank=True, null=True)),
                ("ElectionBlankVote", models.BooleanField(default=False)),
                (
                    "ElectionCode",
                    models.CharField(max_length=200, primary_key=True, serialize=False),
                ),
            ],
        ),
    ]