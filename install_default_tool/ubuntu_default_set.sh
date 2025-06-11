# 1. Install Utils
sudo apt update -y
sudo apt install -y curl wget vim tmux net-tools

# 2. Install Git
sudo apt install git -y

# 3. Install Docker
sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io

# 4. Install NVM
sudo curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.2/install.sh | bash
source ~/.bashrc


# 5. Install pyenv
sudo apt-get install -y make build-essential libssl-dev zlib1g-dev libbz2-dev libreadline-dev libsqlite3-dev wget curl llvm libncurses5-dev libncursesw5-dev xz-utils tk-dev build-essential libssl-dev zlib1g-dev libbz2-dev libreadline-dev libsqlite3-dev wget curl llvm libncurses5-dev libncursesw5-dev xz-utils tk-dev libffi-dev liblzma-dev git libffi-dev
sudo git clone https://github.com/pyenv/pyenv.git ~/.pyenv
sudo echo 'export PYENV_ROOT="$HOME/.pyenv"' >> ~/.bashrc
sudo echo 'export PATH="$PYENV_ROOT/bin:$PATH"' >> ~/.bashrc
sudo echo 'eval "$(pyenv init -)"' >> ~/.bashrc
source ~/.bashrc


# 6. Install SDKMAN
sudo curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"