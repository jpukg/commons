#!/bin/sh
_now=$(date +"%Y%d%m_%H%M%S")
#project_name=""
#war_file_name=""
build_dir=$(pwd)
#svn_address=""

while [ $# -gt 0 ] ; do
    case $1 in
        -h | --help )      shift
        echo "In [project name] dir (It will create it if does not exist) creates an empty dir with current timestamp then,"
        echo "Fetches the latest project from subversion. builds it with default profiles, then with production profile"
        echo "for web and places it in the last_version dir with a time stamp."
        echo "It can be used as follows: "
        echo "\n production-build -p projectName [-w war file name] [-svn svn address][-wd war file dir name]"
        echo "-h --help shows this help."
        echo "-p --proj_name project name"
        echo "-bd --build_dir build path by default is current dir."
        echo "-w --war_file_name [war file name]. default is project name"
        echo "-wd --war_dir [war directory]. default is 'web. It is relative to project directory'"
        echo "-svn --svn_address [project directory address]. default is 'svn://product/var/svn/[project_name]/trunk'."
             exit 0;
                                ;;
        -p | --proj_name )      shift
                                project_name=$1
                                ;;
        -bd | --build_dir )      shift
                                build_dir=$1
                                ;;
        -w | --war_file_name) shift
                                war_file_name=$1
                                ;;
        -wd | --web_dir) shift
                                web_proj_dir=$1
                                ;;
        -svn | --svn_address ) shift
                                svn_address=$1
                                ;;
        * )                     usage
                                exit 1
    esac
    shift
done

if [ -z ${project_name} ]
then
    echo "Please set project name using -p switch."
    exit 1;
fi

proj_root_dir=${build_dir}/${project_name}

if [  -z ${svn_address} ]
then
    svn_address="svn://product/var/svn/${project_name}/trunk"
    echo "INFO: No Subversion address is provided. using '${svn_address}' as default. This can be overrided by -svn switch..."
fi

if [  -z ${war_file_name} ]
then
    war_file_name=${project_name}
    echo "INFO: No war file name provided. using '${project_name}' as default. This can be overrided by -w switch... (file name should be without .war extension) ..."
fi

if [ ! -d "${proj_root_dir}" ]
then
	mkdir ${proj_root_dir}
fi

if [ -z "${web_proj_dir}" ]
then
    web_proj_dir="web"
    echo "INFO: No war file name provided. using '${web_proj_dir}' as default. This can be overrided by -wd switch..."
fi

cd ${proj_root_dir}

proj_dir="${proj_root_dir}/${project_name}_${_now}"
proj_web_dir="${proj_dir}/${web_proj_dir}"

echo "Summary..."
echo "Please verify these are correct."
echo "Project name is: ${project_name}"
echo "Subversion repository address is: ${svn_address}"
echo "Project root dir is: ${proj_root_dir}"
echo "Checkout project for current build in: ${proj_dir}"
echo "Web directory is: ${proj_web_dir}"
echo "War file name is: ${war_file_name}.war"
echo

echo "Start creating project dir : ${proj_dir}"

mkdir ${proj_dir}
cd ${proj_dir}
echo "Checking out project from ${svn_address}"
svn co ${svn_address} .
echo "Building all modules with command: 'mvn clean install' ..."
mvn clean install 
if [ $? -ne 0 ]
then
	echo "Build FAILURE. Project Build failed Please see the result";
	exit 1;
fi
cd ${proj_web_dir}
echo "Building web module for production with command: 'mvn -P secure,production -Dmaven.test.skip=true clean install' ..."
mvn -P secure,production -Dmaven.test.skip=true clean install

if [ $? -ne 0 ]
then
        echo "Build FAILURE. Project Build failed Please see the result";
        exit 2;
fi

cd ${proj_web_dir}/target

_new_file="${war_file_name}${_now}.war"
echo "Appending timestamp to war file. new file name is : ${_new_file}"
cp ${war_file_name}.war ${_new_file}
cd ${proj_root_dir}
_last_version_dir=last_version
if [ ! -d "${_last_version_dir}" ]
then
	mkdir last_version
fi
echo "Cleaning ${_last_version_dir} folder...."
rm -rf ${_last_version_dir}/*
echo "Moving build result in ${_last_version_dir} ..."
mv ${proj_web_dir}/target/${_new_file} ${_last_version_dir} && echo "Done. Here is file path: ${proj_root_dir}/${_last_version_dir}/${_new_file}"


#echo "Connecting to //srv-3/publicshare to copy the file ..."
#smbclient //srv-3/publicshare Tt123@ -W SAMENEA -U j.ashrafi <<EOF
#cd ib
#put $_new_file 
#EOF







