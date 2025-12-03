SUMMARY = "MemryX MX3 USB"
DESCRIPTION = "Kernel module for the MemryX MX3 (USB)"
HOMEPAGE = "http://developer.memryx.com"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://../../LICENSE.md;md5=c9bc5bbf938426cbf4c17ff142b740a9"
INSANE_SKIP:${PN} += "buildpaths"
INSANE_SKIP:${PN}-dbg += "buildpaths"

# Example: Pull source from a Git repository. Adjust SRC_URI as needed.
SRC_URI = "git://github.com/memryx/mx3_driver_pub;protocol=https;branch=release"
SRCREV = "0073a547c8b7762cfbdcd67da8df9bb5f31d55e0"

# Use the working directory provided by BitBake
S = "${WORKDIR}/git/kdriver/linux/usb"

# Inherit the module class to build an out-of-tree kernel module
inherit module

# EXTRA_OEMAKE passes additional parameters to the module Makefile.
# The external module’s Makefile should use the variable KERNELDIR to point
# to the kernel build directory.
EXTRA_OEMAKE = "KERNEL_SRC=${STAGING_KERNEL_DIR}"

# Optionally, if your module should be automatically loaded,
# define the auto-load variable (module name should match the output .ko file).
MODULE_AUTOLOAD = "memx_cascade_usb"

# also install the firmware files
do_install:append() {
  # Create the firmware directory and install the firmware file
  install -d ${D}${nonarch_base_libdir}/firmware
  install -m 0644 ${WORKDIR}/git/firmware/cascade.bin ${D}${nonarch_base_libdir}/firmware/
  install -m 0644 ${WORKDIR}/git/firmware/cascade_4chips_flash.bin ${D}${nonarch_base_libdir}/firmware/
  install -m 0644 ${WORKDIR}/git/firmware/cascade_mini_bar_flash.bin ${D}${nonarch_base_libdir}/firmware/

  # Also install the udev rule
  install -d ${D}${sysconfdir}/udev/rules.d
  install -m 0644 ${WORKDIR}/git/kdriver/60-memx-drivers.rules ${D}${sysconfdir}/udev/rules.d/
}

FILES:${PN} += "${nonarch_base_libdir}/firmware/cascade.bin"
FILES:${PN} += "${nonarch_base_libdir}/firmware/cascade_4chips_flash.bin"
FILES:${PN} += "${nonarch_base_libdir}/firmware/cascade_mini_bar_flash.bin"
FILES:${PN} += "${sysconfdir}/udev/rules.d/60-memx-drivers.rules"
FILES:${PN}-dbg += "${nonarch_base_libdir}/firmware/cascade.bin"
FILES:${PN}-dbg += "${nonarch_base_libdir}/firmware/cascade_4chips_flash.bin"
FILES:${PN}-dbg += "${nonarch_base_libdir}/firmware/cascade_mini_bar_flash.bin"
FILES:${PN}-dbg += "${sysconfdir}/udev/rules.d/60-memx-drivers.rules"
