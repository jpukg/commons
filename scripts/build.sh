#!/bin/sh
#todo -bd should take effect regardless of ordering
build_dir=$(pwd)
while [ $# -gt 0 ] ; do
    case $1 in
        -h | --help )
        echo "use this way: ./build -bd [build path] [projects names separated by space]"
        echo "Note -bd should be the first parameter to take effect!"
        echo "build path by default is current dir."
        echo "for example: ./build seapay payments"
        echo "this will create a dir with the name of project in the current dir and will place "
        echo "a directory with current time stamp for the current build in that dir the."
        echo "Result of last build will be available in [project name]/last_version directory"
        echo "If you want more control on build params use production-build.sh instead"
        echo "In that case use './production-build -h' to see the usage details."
             exit 0;
                                ;;
        seapay )
                ./production-build.sh -p seapay -bd ${build_dir}
                                ;;
        -bd | --build_dir )     shift
                                build_dir=$1
                                ;;
        internetbanking | payments)
                ./production-build.sh -p internetbanking -wd payments/web -w payments  -bd ${build_dir}
                                ;;
        smspanel )
            ./production-build.sh -p smspanel -svn svn://product/var/svn/repo/smspanel/trunk -wd war -w smspanel-war  -bd ${build_dir}
                                ;;
        sms )
            ./production-build.sh -p sms -svn svn://product/var/svn/repo/sms/trunk -wd war -w sms-war -bd ${build_dir}
                                ;;
        * )                     usage
                                exit 1
    esac
    shift
done
