SUMMARY = "MemryX Runtime"
DESCRIPTION = "MemryX Core Runtime (libmemx) and C++ MxAccl Runtime API"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=878eb771d03fe953de60efc07878596c"
INSANE_SKIP:${PN} += "already-stripped dev-elf installed-vs-shipped"
INSANE_SKIP:${PN}-dev += "already-stripped dev-elf"
INSANE_SKIP:${PN}-dbg += "already-stripped dev-elf"

DEPENDS += "util-linux-libuuid"

SRC_URI = "git://github.com/memryx/MxAccl;protocol=https;branch=release"
SRCREV = "8ecd7ba1a780dee0a18cf5163a940c27a8cc76ef"

S = "${WORKDIR}/git"

# Inherit the CMake class to use CMake for building.
inherit cmake

# use bitbake's provided CFLAGS instead of our optimized (and potentially wrong) AVX2, etc., flags
EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=Packaging -DCMAKE_X86_FLAGS_BASE=0 -DCMAKE_X86_FLAGS_AVX2=0 -DCMAKE_AARCH64_FLAGS=0 -DCMAKE_RISCV_FLAGS=0 "

do_configure:prepend() {
    echo "Copying libmemx header file to source..."
    install -d ${STAGING_INCDIR}/memx/
    install -m 0644 ${S}/misc/libmemx/memx.h ${STAGING_INCDIR}/memx/

    echo "Copying libmemx for this architecture to source..."
    install -d ${STAGING_LIBDIR}/
    install -m 0644 ${S}/misc/libmemx/${TARGET_ARCH}/libmemx.so ${STAGING_LIBDIR}/
}

do_install:append() {

    echo "Copying libmemx header file to final install..."
    install -d ${D}/usr/include/memx/
    install -m 0644 ${S}/misc/libmemx/memx.h ${D}/usr/include/memx/

    echo "Copying libmemx for this architecture to final install..."
    install -d ${D}/usr/lib/
    install -m 0644 ${S}/misc/libmemx/${TARGET_ARCH}/libmemx.so.2 ${D}/usr/lib/
    
    echo "Copying mxa_manager.conf to final install..."
    install -d ${D}/etc/memryx/
    install -m 0644 ${S}/mxa_manager/mxa_manager.conf ${D}/etc/memryx/
}


FILES:${PN} = "${bindir}/acclBench"
FILES:${PN} += "${bindir}/mxa_manager"
FILES:${PN} += "${etcdir}/memryx/mxa_manager.conf"
FILES:${PN} += "${libdir}/libmemx.so"
FILES:${PN} += "${libdir}/libmx_accl.so.2"
FILES:${PN} += "${libdir}/libmx_accl.so"
FILES:${PN}-dev = "${incdir}/memx"
FILES:${PN}-dev += "${incdir}/memx/memx.h"
FILES:${PN}-dev += "${incdir}/memx/accl"
FILES:${PN}-dev += "${incdir}/memx/accl/client.h"
FILES:${PN}-dev += "${incdir}/memx/accl/DeviceManager.h"
FILES:${PN}-dev += "${incdir}/memx/accl/dfp.h"
FILES:${PN}-dev += "${incdir}/memx/accl/DFPRunner.h"
FILES:${PN}-dev += "${incdir}/memx/accl/messages.h"
FILES:${PN}-dev += "${incdir}/memx/accl/MxAcclBase.h"
FILES:${PN}-dev += "${incdir}/memx/accl/MxAccl.h"
FILES:${PN}-dev += "${incdir}/memx/accl/MxAcclMT.h"
FILES:${PN}-dev += "${incdir}/memx/accl/prepost.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/blocky_queue.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/cpu_opts.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/errors.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/featureMap.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/gbf.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/id_tracker.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/locked_var.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/macros.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/mxpack.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/mxTypes.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/path.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/sha512.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/thread_pool.h"
FILES:${PN}-dev += "${incdir}/memx/accl/utils/uint128.h"
