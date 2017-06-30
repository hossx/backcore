Backcore
========

Step to build up entire exchange(for mac):

Prepare:
---
    brew install mongo,redis,leveldb
    git clone git@github.com:hossx/backcore.git
    cd backcore/bitway
    ./prepare.sh // This step still has some defects

Publish local:
---
    git clone git@github.com:hossx/akka-persistence-hbase.git
    cd akka-persistence-hbase
    ./activator publish-local

    cd backcore/coinex
    ./activator publish-local

Start services:
---
    # NOTE need download and start bitcoin wallet and/or alt coin wallet
    sudo mongod
    sudo redis-server

    cd backcore
    ./run.sh

    cd frontend/exchange2
    ./run.sh

    git clone git@github.com:hossx/admin.git
    cd admin
    ./run.sh // This may cause port conflict with exchange2. change the another one

    // This doesn't work now
    cd backcore/bitway
    ./run.sh

To be solve:
---
- email/sms don't work now. need recharge the email/sms service **@孔亮 @王东**
- need deplay hdfs/hbase to aws and deploy all of these services to aws **@伟超，@小露**
- change the logo of coinport **@王东**
- close open source feature? (need discuss) **@王东**
- Bitway doesn't work. **@杨立**


