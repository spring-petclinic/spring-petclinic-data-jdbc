FROM gitpod/workspace-full:2022-10-25-06-57-58

SHELL ["/bin/bash", "-c"]

RUN wget https://github.com/CRaC/openjdk-builds/releases/download/17-crac%2B3/openjdk-17-crac+3_linux-x64.tar.gz
RUN sudo tar zxf openjdk-17-crac+3_linux-x64.tar.gz \
    && sudo mv openjdk-17-crac+3_linux-x64 /opt/
RUN echo 'export JAVA_HOME=/opt/openjdk-17-crac+3_linux-x64/' >> /home/gitpod/.bashrc \
    && echo 'export PATH=/opt/openjdk-17-crac+3_linux-x64/bin:$PATH' >> /home/gitpod/.bashrc