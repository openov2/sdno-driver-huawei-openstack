#!/bin/bash
# Copyright 2016 Huawei Technologies Co., Ltd.
#   
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#   
#      http://www.apache.org/licenses/LICENSE-2.0
#   
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#check user
if [ "root" = "`/usr/bin/id -u -n`" ];then
    echo "root has been forbidden to execute the shell."
    exit 1
fi

if [[ -z ${APP_ROOT} ]];then
     echo "APP_ROOT is empty."
     exit 1
fi

if [[ -z ${_APP_LOG_DIR} ]];then
     echo "_APP_LOG_DIR is empty."
     exit 1
fi

#HORNETQ_CONF=$APP_ROOT/etc

#chmod 400 $HORNETQ_CONF/engine.json
