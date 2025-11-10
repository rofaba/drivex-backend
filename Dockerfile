FROM ubuntu:latest
LABEL authors="RodrigoFaure"

ENTRYPOINT ["top", "-b"]