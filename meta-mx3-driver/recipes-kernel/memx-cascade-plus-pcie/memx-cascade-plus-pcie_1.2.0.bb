SUMMARY = "MemryX MX3 PCIe"
DESCRIPTION = "Kernel module for the MemryX MX3 (PCIe)"
HOMEPAGE = "http://developer.memryx.com"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://../../LICENSE.md;md5=c9bc5bbf938426cbf4c17ff142b740a9"
INSANE_SKIP:${PN} += "buildpaths"
INSANE_SKIP:${PN}-dbg += "buildpaths"

# Example: Pull source from a Git repository. Adjust SRC_URI as needed.
SRC_URI = "git://github.com/memryx/mx3_driver_pub;protocol=https;branch=release"
SRCREV = "e0e8c1bf46716aa298f09d64c43b4ea71cf3a8d2"

# Use the working directory provided by BitBake
S = "${WORKDIR}/sources-unpack/git/kdriver/linux/pcie"

# Inherit the module class to build an out-of-tree kernel module
inherit module

# EXTRA_OEMAKE passes additional parameters to the module Makefile.
# The external module’s Makefile should use the variable KERNELDIR to point
# to the kernel build directory.
EXTRA_OEMAKE = "KERNEL_SRC=${STAGING_KERNEL_DIR}"

# Optionally, if your module should be automatically loaded,
# define the auto-load variable (module name should match the output .ko file).
MODULE_AUTOLOAD = "memx_cascade_plus_pcie"

# also install the firmware files
do_install:append() {
  # Create the firmware directory and install the firmware file
  install -d ${D}${nonarch_base_libdir}/firmware
  install -m 0644 ${WORKDIR}/sources-unpack/git/firmware/cascade.bin ${D}${nonarch_base_libdir}/firmware/
  install -m 0644 ${WORKDIR}/sources-unpack/git/firmware/cascade_4chips_flash.bin ${D}${nonarch_base_libdir}/firmware/
}

FILES:${PN} += "${nonarch_base_libdir}/firmware/cascade.bin"
FILES:${PN} += "${nonarch_base_libdir}/firmware/cascade_4chips_flash.bin"
FILES:${PN}-dbg += "${nonarch_base_libdir}/firmware/cascade.bin"
FILES:${PN}-dbg += "${nonarch_base_libdir}/firmware/cascade_4chips_flash.bin"
